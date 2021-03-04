package com.wix.katas.futures

import com.wix.katas.futures.Blog.PostNotFound
import org.specs2.concurrent.ExecutionEnv
import org.specs2.matcher.FutureMatchers
import org.specs2.mutable.SpecWithJUnit

import scala.concurrent.ExecutionContext
import scala.util.{Failure, Success}

class FutureKata4Test(implicit ee: ExecutionEnv) extends SpecWithJUnit with FutureMatchers {

  implicit private val ec: ExecutionContext = ExecutionContext.global

  class Kata4Blog extends FutureKata4 with FakeBlog

  private val blog = new Kata4Blog()

  "get authors of posts 1, 2, 3" >> {
    val postIds = List(1, 2, 3)
    val expected = List(Success("author1"), Success("author2"), Success(""))
    blog.getPostAuthors(postIds) must beEqualTo(expected).await
  }

  "get authors of posts 1, 2 and a failure for post 4" >> {
    val postIds = List(1, 2, 4)
    val expected = List(Success("author1"), Success("author2"), Failure(PostNotFound(postId = 4)))
    blog.getPostAuthors(postIds) must beEqualTo(expected).await
  }
}
