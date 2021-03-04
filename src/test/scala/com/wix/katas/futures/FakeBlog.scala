package com.wix.katas.futures

import scala.concurrent.Future

trait FakeBlog extends Blog {

  import Blog._

  private val blogPosts = Map(
    1 -> BlogPost(1, "author1", "content1"),
    2 -> BlogPost(2, "author2", "content2"),
    3 -> BlogPost(3, "", "content3")
  )

  private val blogSettings = Map(
    10 -> BlogSettings(10, "author10"),
    20 -> BlogSettings(20, "author20"),
    30 -> BlogSettings(30, "")
  )

  def getPost(postId: PostId): Future[BlogPost] = blogPosts.get(postId) match {
    case Some(blogPost) => Future.successful(blogPost)
    case None => Future.failed(PostNotFound(postId))
  }

  def getSettings(blogId: BlogId): Future[BlogSettings] = blogSettings.get(blogId) match {
    case Some(blogSettings) => Future.successful(blogSettings)
    case None => Future.failed(SettingsNotFound(blogId))
  }
}
