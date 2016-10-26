INCLUDES
====

When you code, you open a file, open parenthesis, and obtain something

After, you do objects, prototypes, or whatever, but you have to open a new file, recompile etc, which is somewhat frustrating in your workflow

Loading a file with ".load" returns the last expression, like any function.

But can you manage it better ? Can you use yout whole folder like any other object ?

By example, you just code something, often you do

(
a=something
crazy code with open var
)

But your gonna mess up with local var, and you need more precise var names. So, next, you do

(
var crazyVar

myCode
)

And finally, we finish with many
~my obj, ~myFunc, ~ohAndIforgotThisOne

And after maybe
(
~myfinalobj=(myvars:myalgos,all:~myVars)
)

All the point is the balance between fixed objects and variables protos. This is complicated. This is program life.

object
==

And finally you go with object

CRAZYTHING { }

Wich is like a main

Maybe, before the last Object converting operation, you need to keep the plasticity of scripting, because its important to fix or avoid bugs

Let's say I have 3 files

I write

MyCoolApp : APP

// file
(just = ~anIdea)
soCool.scd

// and after, in another file
(
~bobi = MyCoolApp.soCool
)

In fact, its like an include function, wich really lacks in SC. 


The more you go, the more you can collect your ideas, and finally
