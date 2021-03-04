package com.wix.katas.futures

import com.wix.katas.futures.Blog._

import scala.concurrent.{ExecutionContext, Future}

trait FutureKata1 extends Blog {

  // Invoke "getPost" and if the post author is empty then invoke "getSettings"
  // and if the settings author is empty then fail with exception "EmptyAuthor".
  // Fail if either "getPost" or "getSettings" fails.

  def getNonEmptyAuthor(postId: PostId, blogId: BlogId)(implicit ec: ExecutionContext): Future[String] = ???
}
