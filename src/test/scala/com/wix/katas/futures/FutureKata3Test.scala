package com.wix.katas.futures

import com.wix.katas.futures.Blog.PostNotFound
import org.specs2.concurrent.ExecutionEnv
import org.specs2.matcher.FutureMatchers
import org.specs2.mutable.SpecWithJUnit

import scala.concurrent.ExecutionContext

class FutureKata3Test(implicit ee: ExecutionEnv) extends SpecWithJUnit with FutureMatchers {

  implicit private val ec: ExecutionContext = ExecutionContext.global

  class Kata3Blog extends FutureKata3 with FakeBlog

  private val blog = new Kata3Blog()

  "get authors of posts 1, 2, 3" >> {
    val postIds = List(1, 2, 3)
    val expected = List("author1", "author2", "")
    blog.getPostAuthors(postIds) must beEqualTo(expected).await
  }

  "fail because of a not found post" >> {
    val postIds = List(1, 2, 4)
    blog.getPostAuthors(postIds) must throwA(PostNotFound(postId = 4)).await
  }

}
