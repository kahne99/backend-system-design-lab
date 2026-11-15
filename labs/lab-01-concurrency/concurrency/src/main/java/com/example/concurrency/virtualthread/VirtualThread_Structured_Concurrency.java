package com.example.concurrency.virtualthread;


/*
* It addresses issues regarding how to manage concurrent tasks, ensure that these tasks share the same life cycle
* and that errors are handle consistency.
*
* Issues with Traditional Consistency:
* + Task lifecycle: tasks are handle independently. Its lifecycle depend on developer.
* + Error handling: Each task need to be managed independently.
* + Cancellation: Subtasks must be canceled if their parent tasks are canceled.
* */
public class VirtualThread_Structured_Concurrency {

    // `StructuredTaskScope` is supported as a preview feature (Java 21), will look into it later.

}
