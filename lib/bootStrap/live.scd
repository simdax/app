{
	arg path=thisProcess.nowExecutingPath,method=\write;
	var base=BootStrap.base;
	var parent=
	(
		path:path,
		dirname:{arg s; s.path.dirname},
		name:{arg s; s.dirname.basename},
	);
	var proto=
	(
		files:(
			asFile:{arg s,p; s.dirname+/+p++".sc"},
			write:{arg s,name,content;
				File.open(s.asFile(name),"w").write(content).close},
			delete:{arg s,name; File.delete(s.asFile(name))}
		),
		dirs:(
			asFile:{arg s,p; s.dirname+/+p},
			write:{arg s,name; s.asFile(name).mkdir},
			delete:{arg s,name; ("rm -R "++s.asFile(name)).unixCmd}
		)
	);

	BootStrap.perform(method,parent.dirname,base,parent,proto)
}

