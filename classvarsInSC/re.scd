
// IA {classvar <>io=7;}
// IO : IA { }
// IU : IA { }

// IO.io = 8
// IU.io

// IA {classvar <>io=7; *post{io.postln}}
// IO : IA {classvar <>io; }

// IA.io === IA.io // true => unexpected

// simply init classvar
// IA {classvar <>io=7; *post{io.postln}}
// IO : IA {classvar <>io=nil; }

// // IA.io === IA.io // false => expected

//IA.io = 7 ; IO.io=7

// IA {classvar <>io=7; *post{this.io.postln}}
// IO : IA {classvar <>io=6; }

// IA.io = 7 ; IO.io=6

// initClass is not inerited

// BOB{
// 	*initClass{"io".postln}
// }
// BOBI :BOB {}
// BOBIBI : BOBI {}

//SO we need another impletation whit 'this'

