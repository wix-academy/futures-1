package com.wix.katas.futures

import com.wix.katas.futures.Blog.PostId

import scala.concurrent.{ExecutionContext, Future}

trait FutureKata3 extends Blog {

  // Invoke "getPost" for all elements of "postIds" concurrently.
  // "Fail fast", that is fail if any of the "getPost" invocations fails.
  def getPostAuthors(postIds: List[PostId])(implicit ec: ExecutionContext): Future[List[String]] = ???
}
