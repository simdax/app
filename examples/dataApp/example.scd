(
j=(a:8, b:5);
Bob.save(j)
)

(
// second Time
Library.postTree
)

// you can use it for gui prototyping
(
Library.at(\Bob).push;
b=currentEnvironment;
w=FlowView();
a= ProtoGui()%=
(fen:w, envir:b,
gui:EZSlider,opt:(controlSpec:[0,12].asSpec));
(a.copy.put(\model,\a)).go();
(a.copy.put(\model,\b)).go();
Environment.pop;
)

