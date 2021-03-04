package com.wix.katas.futures

import com.wix.katas.futures.Blog.{BlogPost, PostId}

import scala.concurrent.{ExecutionContext, Future}

trait FutureKata2 extends Blog {

  // ------------------------------------------------------------------------------------------------------------------
  // GetPostResult
  // ------------------------------------------------------------------------------------------------------------------

  sealed trait GetPostResult

  case class GetPostSuccess(post: BlogPost, exceptions: List[Throwable]) extends GetPostResult

  case class GetPostFailure(exceptions: List[Throwable]) extends GetPostResult

  // ------------------------------------------------------------------------------------------------------------------
  // getPostWithRetries
  // ------------------------------------------------------------------------------------------------------------------

  // Invoke "getPost" and retry it if "getPost" fails until the number of invocations == "maxAttempts"
  def getPostWithRetries(postId: PostId, maxAttempts: Int)(implicit ec: ExecutionContext): Future[GetPostResult] = ???
}
