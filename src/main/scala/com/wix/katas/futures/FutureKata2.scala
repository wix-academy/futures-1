package com.wix.katas.futures

import com.wix.katas.futures.Blog.{BlogPost, PostId}

import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success}

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
  def getPostWithRetries(postId: PostId, maxAttempts: Int)(implicit ec: ExecutionContext): Future[GetPostResult] = {

    require(maxAttempts > 0, s"maxAttempts [$maxAttempts] should be > 0")

    def loop(exceptions: List[Throwable], attempts: Int)(implicit ec: ExecutionContext): Future[GetPostResult] = {
      getPost(postId).transformWith {
        case Success(post) => Future.successful(GetPostSuccess(post, exceptions.reverse))
        case Failure(e) if attempts < maxAttempts => loop(e :: exceptions, attempts + 1)
        case Failure(e) => Future.successful(GetPostFailure((e :: exceptions).reverse))
      }
    }

    loop(Nil, attempts = 1)
  }
}
