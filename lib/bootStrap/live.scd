{
	arg path=thisProcess.nowExecutingPath,method=\write;
	var parent=
	(
		path:path,
		root:{arg s;
			PathName(s.path).isFile.if{s.path.dirname}
			{s.path}
		},
		name:{arg s; s.root.basename},
	);
	var base=BootStrap.base(parent.name);
	var proto=
	(
		files:(
			asFile:{arg s,p,ext="sc"; s.root+/+p++"."++ext},
			write:{arg s,name,content;
				content=content.asArray;
				File.open(s.asFile(name,content[1]),"w").write(content[0]).close},
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

