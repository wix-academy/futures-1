package com.wix.katas.futures

import com.wix.katas.futures.Blog.PostId

import scala.concurrent.{ExecutionContext, Future}
import scala.util.Try

trait FutureKata4 extends Blog {

  // Invoke "getPost" for all elements of "postIds" concurrently and
  // accumulate the results of all "getPost" invocations and return these results
  def getPostAuthors(postIds: List[PostId])(implicit ec: ExecutionContext): Future[List[Try[String]]] = ???
}
