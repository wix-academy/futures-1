package com.wix.katas.futures

import com.wix.katas.futures.Blog.PostId

import scala.concurrent.{ExecutionContext, Future}
import scala.util.Try

trait FutureKata5 extends Blog {

  // Suppose "postIds" contains duplicates, so there is no need to invoke "getPost" for each element of "postIds".
  // Thus invoke "getPost" only for _unique_ elements of "postIds" concurrently and the results for all "postIds".
  def getPostAuthorsWithDuplicates(postIds: List[PostId])(implicit ec: ExecutionContext): Future[List[Try[String]]] = {

    val getAuthor = { postId: PostId => getPost(postId).map(_.author) }
    val getPostId2Author = { postId: PostId => getAuthor(postId).transformWith(result => Future.successful(postId -> result)) }
    val future = Future.traverse(postIds.toSet)(getPostId2Author)

    future.map(pairs => postIds.map(pairs.toMap))
  }
}
