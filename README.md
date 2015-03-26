# CSCE 355 Project
### Creating, Simulating, Modifying DFA's

*The following copied from Dr. Fenner's original project specifications.*

For the programming portion of the course (10% of my grade) I am to write programs that perform two or more of a choice of six tasks involving DFAs. I may choose to implement two or more of the following functions:

I am only required to implement two of the six tasks below (my choice) -- any additional functionality will earn me extra credit ("1% of my final grade per additional task correctly implemented").

##### (1) Simulate a DFA

After reading the description of a DFA *N* from a text file, read any number of strings over *N*'s alphabet from another text file, indicating (to `stdout`) whether *N* accepts or rejects each of the strings.

*Status*: **Implemented**

##### (2) Minimizing a DFA

Read the desription of a DFA from a text file and write (to `stdout`) the description of a minimal equivalent DFA.

*Status*: **Implemented**

##### (3) Text Search

Read a string *w* from a text, and write to `stdout` the description of a DFA that accepts a string *x* if and only if *w* is a substring of *x*.

*Status*: **Not implemented**

##### (4) Complement and Intersection

Read one or two descriptions of DFAs from text files (depending on the type of transformation asked for) and write to `stdout` a description of the transformed DFA &mdash; either the complementary DFA or the product construction.

*Status*: **Not implemented**

##### (5) Inverse Homomorphic Image

Read the descriptions of a DFA *N* and a homomorphism *h* and write to `stdout` the description of a DFA recognizing *h<sup>-1</sup>(L(A))*.

*Status*: **Not implemeted**

##### (6) Determining Properties of a DFA

Read from a text file the descriptions of a DFA *A* and write to `stdout` whether or not *L(A)* is empty and whether or not *L(A)* is infinite.

*Status*: **Not implemented**
