{
	arg path=thisProcess.nowExecutingPath,method=\write;
	var base=BootStrap.base;
	var parent=
	(
		path:path,
		root:{arg s;
			PathName(s.path).isFile.if{s.path.dirname}
			{s.path}
		},
		name:{arg s; s.root.basename},
	);
	var proto=
	(
		files:(
			asFile:{arg s,p; s.root+/+p++".sc"},
			write:{arg s,name,content;
				File.open(s.asFile(name),"w").write(content).close},
			delete:{arg s,name; File.delete(s.asFile(name))}
		),
		dirs:(
			asFile:{arg s,p; s.root+/+p},
			write:{arg s,name; s.asFile(name).mkdir},
			delete:{arg s,name; ("rm -R "++s.asFile(name)).unixCmd}
		)
	);

	BootStrap.perform(method,parent.root,base,parent,proto)
}


