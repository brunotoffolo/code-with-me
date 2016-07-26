# Streams in Java 8

This is just a simple project used to demonstrate the power of stream operations
introduced in Java 8. It was originally presented in a knowledge transfer session
regarding Java 8 new features. The code is essentially composed of three different
main applications that can be used as an example of how it is possible to work
with this new functionality introduced in the language.

The `BookParser` class contains an example of how the same operations can be done
in the usual Java 7 way, with a `for-each` loop traversing a list of strings (that
represent the contents of a book) and calculating some statistics on top of it.
After that, the same strategy is applied using Java 8 streams to demonstrate how
easy and simple it is to perform the same thing while taking advantage of this new
feature.

Similarly, the `ExamResultAnalyzer` class traverses a very huge list of exam
results (a simple entity containing the student ID and grade) to retrieve, in
ascending order, the IDs of the students who got the 100 highest notes in the
exam. The class has a method that performs this with traditional `for-each` loops
and another method that does the same thing using a sequential stream.

The `InfiniteStream` class demonstrates infinite streams and some computations
that may be done with them.


## How to run the code

To keep the repository concise and clean, IDE-specific files were not commited.
As this is a simple application, just clone the repository and run any class in the
`com.brunotoffolo.codewithme.streams.business` package. Some detailed tutorials on
how to do that are available for
[IntelliJ IDEA](https://www.jetbrains.com/help/idea/2016.1/running-applications.html),
[NetBeans](https://netbeans.org/kb/docs/java/quickstart.html#run) and
[Eclipse](http://stackoverflow.com/a/12546688/3227787).

Log messages were inserted in the code, through simple `System.out` messages, to
make it easier to follow the order in which the commands were invoked by simply
examining the console output after the application is run.

Have fun enjoying this demonstration!


## License for the book

To perform the computations in the `BookParser` class, the book
["The Adventures of Tom Sawyer", from Project Gutenberg](https://www.gutenberg.org/ebooks/74),
was used as input. Gutenberg is a project which offers over 50,000 free e-books
that were previously published by _bona fide_ publishers. The Gutenberg staff
digitized and proofread them with the help of thousands of volunteers, and now
make this content available for free for anyone interested.