{
	arg path=thisProcess.nowExecutingPath;
	var base=
	(app:{arg s; s.name.capitalize++" : APP {}"});
	var parent=
	(
		path:path,
		name:{arg s; s.path.dirname.basename},
		dirname:{arg s; s.path.dirname},
		asFile:{arg s,p; s.dirname+/+p++".sc"},
		write:{arg s,name,content;
			File.open(s.asFile(name),"w").write(content).close}
	);
	var r=base =% parent;
	r.keysValuesDo { |k,v|
		r.write(k.asString,v.value(r))
	};
	"bootStrap executed".inform
}