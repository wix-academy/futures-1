package com.wix.katas.futures

import com.wix.katas.futures.Blog.{BlogPost, PostId}
import org.specs2.concurrent.ExecutionEnv
import org.specs2.matcher.FutureMatchers
import org.specs2.mutable.SpecWithJUnit
import org.specs2.specification.Scope

import scala.concurrent.{ExecutionContext, Future}

class FutureKata2Test(implicit ee: ExecutionEnv) extends SpecWithJUnit with FutureMatchers {

  implicit private val ec: ExecutionContext = ExecutionContext.global

  // ------------------------------------------------------------------------------------------------------------------
  // Test Scope
  // ------------------------------------------------------------------------------------------------------------------

  class Kata2Blog(maxFailures: Int) extends FutureKata2 with FakeBlog with Scope {

    case class GetPostTempException(postId: PostId, attempt: Int) extends Exception

    private var failures = 0

    override def getPost(postId: PostId): Future[BlogPost] = {
      failures = failures + 1
      if (failures > maxFailures) super.getPost(postId) else Future.failed(GetPostTempException(postId, failures))
    }
  }

  // ------------------------------------------------------------------------------------------------------------------
  // Tests
  // ------------------------------------------------------------------------------------------------------------------

  "get post on the 1st attempt" in new Kata2Blog(maxFailures = 0) {
    val post = BlogPost(postId = 1, "author1", "content1")
    val exceptions = Nil
    val expected = GetPostSuccess(post, exceptions)
    getPostWithRetries(postId = 1, maxAttempts = 10) must beEqualTo(expected).await
  }

  "get post on the 2nd attempt of 3" in new Kata2Blog(maxFailures = 1) {
    val post = BlogPost(postId = 1, "author1", "content1")
    val exceptions = List(GetPostTempException(post.postId, attempt = 1))
    val expected = GetPostSuccess(post, exceptions)
    getPostWithRetries(postId = 1, maxAttempts = 3) must beEqualTo(expected).await
  }

  "get post on the 3d attempt of 3" in new Kata2Blog(maxFailures = 2) {
    val post = BlogPost(postId = 1, "author1", "content1")
    val exceptions = List(
      GetPostTempException(post.postId, attempt = 1),
      GetPostTempException(post.postId, attempt = 2)
    )
    val expected = GetPostSuccess(post, exceptions)
    getPostWithRetries(postId = 1, maxAttempts = 3) must beEqualTo(expected).await
  }

  "fail to get post after 1 attempt" in new Kata2Blog(maxFailures = 10) {
    val exceptions = List(GetPostTempException(postId = 1, attempt = 1))
    val expected = GetPostFailure(exceptions)
    getPostWithRetries(postId = 1, maxAttempts = 1) must beEqualTo(expected).await
  }

  "fail to get post after 2 attempts" in new Kata2Blog(maxFailures = 10) {
    val exceptions = List(
      GetPostTempException(postId = 1, attempt = 1),
      GetPostTempException(postId = 1, attempt = 2)
    )
    val expected = GetPostFailure(exceptions)
    getPostWithRetries(postId = 1, maxAttempts = 2) must beEqualTo(expected).await
  }
}
