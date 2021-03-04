package com.wix.katas.futures

import org.specs2.concurrent.ExecutionEnv
import org.specs2.matcher.FutureMatchers
import org.specs2.mutable.SpecWithJUnit

import scala.concurrent.ExecutionContext
import scala.util.Success

class FutureKata5Test(implicit ee: ExecutionEnv) extends SpecWithJUnit with FutureMatchers {

  implicit private val ec: ExecutionContext = ExecutionContext.global

  class Kata5Blog extends FutureKata5 with FakeBlog

  private val blog = new Kata5Blog()

  "get authors of unique posts 1, 2, 3" >> {
    val postIds = List(1, 2, 3)
    val expected = List(Success("author1"), Success("author2"), Success(""))
    blog.getPostAuthorsWithDuplicates(postIds) must beEqualTo(expected).await
  }

  "get authors of non-unique posts 1, 2, 1" >> {
    val postIds = List(1, 2, 1)
    val expected = List(Success("author1"), Success("author2"), Success("author1"))
    blog.getPostAuthorsWithDuplicates(postIds) must beEqualTo(expected).await
  }
}
