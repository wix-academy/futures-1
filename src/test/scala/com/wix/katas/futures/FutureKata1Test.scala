package com.wix.katas.futures

import com.wix.katas.futures.Blog.{EmptyAuthor, PostNotFound, SettingsNotFound}
import org.specs2.concurrent.ExecutionEnv
import org.specs2.matcher.FutureMatchers
import org.specs2.mutable.SpecWithJUnit

import scala.concurrent.ExecutionContext

class FutureKata1Test(implicit ee: ExecutionEnv) extends SpecWithJUnit with FutureMatchers {

  implicit private val ec: ExecutionContext = ExecutionContext.global

  class Kata1Blog extends FutureKata1 with FakeBlog

  private val blog = new Kata1Blog()

  "get post author successfully" >> {
    blog.getNonEmptyAuthor(postId = 1, blogId = 10) must beEqualTo("author1").await
  }

  "fail to get author if getPost failed" >> {
    blog.getNonEmptyAuthor(postId = 5, blogId = 10) must throwA(PostNotFound(postId = 5)).await
  }

  "get settings author if post author is empty" >> {
    blog.getNonEmptyAuthor(postId = 3, blogId = 10) must beEqualTo("author10").await
  }

  "fail to get author because settings are not found" >> {
    blog.getNonEmptyAuthor(postId = 3, blogId = 50) must throwA(SettingsNotFound(blogId = 50)).await
  }

  "fail to get author because settings author is empty" >> {
    blog.getNonEmptyAuthor(postId = 3, blogId = 30) must throwA(EmptyAuthor(postId = 3, blogId = 30)).await
  }
}
