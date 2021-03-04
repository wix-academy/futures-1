# Exercises on Scala Futures

## Introduction

`Futures` provide a simple way to run an asynchronous computation. 
Future starts a computation when you create it and then eventually returns the result. 
For example, every RPC invocation at Wix is a function that returns a `Future` of the RPC service result. 
See more information about `Futures` API in the [documentation](https://www.scala-lang.org/api/2.12.2/scala/concurrent/Future.html)

`Futures` are composable. We consider the following common cases of `Futures` composition:

- Sequential composition with or without error accumulation;
- Concurrent composition with or without error accumulation;

See the details below.

## Exercises

### Kata1 "Fallback"

This is an exercise on the sequential `Futures` composition without error accumulation.
This exercise uses composition of both sync and async operations.

### Kata2 "Retry"

This is an exercise on the sequential `Futures` composition with error accumulation.
This exercise uses a recursion although a non-recursive solution would be better.

### Kata3 "Traverse"

This is an exercise on the concurrent `Futures` composition without error accumulation.
This is just a straightforward use of [Future.traverse](https://www.scala-lang.org/api/2.12.2/scala/concurrent/Future$.html#traverse[A,B,M[X]%3C:TraversableOnce[X]](in:M[A])(fn:A=%3Escala.concurrent.Future[B])(implicitcbf:scala.collection.generic.CanBuildFrom[M[A],B,M[B]],implicitexecutor:scala.concurrent.ExecutionContext):scala.concurrent.Future[M[B]]).

### Kata4 and Kata5 "Traverse with errors accumulation" 

These are exercises on the concurrent `Futures` composition with error accumulation.
