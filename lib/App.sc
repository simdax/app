APP{
	*root{
		^this.filenameSymbol.asString.dirname;
	}
	*find{arg selector;
		^PathName(this.root)
		.deepFiles() // it
		// TODO to avoid confusion, tell if multiple name ?
		.detect{arg x; x.fileName==(selector++".scd") }
		.absolutePath
	}
	*doesNotUnderstand{ arg selector ... args;
		// TODO search in folder
		var file = this.find(selector);
		^
		selector.isSetter.if{ //write a file
			File.use(file, "w", {arg f;
				f.write(args.join($\n).asString)
			})
		}
		{ //load a file
			try({file.load.valueIfNeeded(*args)}, 
				{ arg error;
					error.errorString.warn;
					Error("bug with file : "++file).throw
				})
		}
	}
	*bootstrap{ arg func;
		^BootStrap(this.root).perform(func)
	}
	*allRecur{
		var f={arg path;
			PathName(path).entries.collect{
				arg entrie;
				if(entrie.isFolder){
					f.(entrie)
				}
				{
					if(entrie.extension==".scd", {
						entrie.load
					});
				}
			}
		};
		^f.value(this.root)
	}
	*filesDo{ arg f;
		PathName(this.root).entries
		.select{arg f; f.extension=="scd"}.do(f)
	}
	// gotAppFile{
	// 	^
	// }
}

APP2 : APP {
	
	// returns an object
	// it serves so you still can modify and use in a live
	// envir, and after, poutring some OOP formatting

	// have to parse a little
	*findLastBrack{arg z; //z="((()))(()))";
		var a=z.findAll($();
		var b=z.findAll($));		
		(a+++b).pairsDo{ arg a,b,i;
			if(a.last<b.first)
			{^a.last}
		};
		Error("que dalle, il est pourri ton algo mec!").throw
	}
	*replacePar{ arg str;
		var b=str.collectAs(_.value,Array);
		var f=b.detectIndex(_==$();
		var g=(b.size-1) - b.reverse.detectIndex(_==$));
		b[f]=${;
		b[g]=$};
		^b.join
	}
	*parse{
		arg file; 
		var res=File.open(file, "r").readAllString
		.replace($",'\"');
		// only first declaration
		var io=res[res.find($()..this.findLastBrack(res)];
		^this.replacePar(io);
	}
	*doesNotUnderstand{ arg selector ... args;
		// TODO search in folder
		var file = this.find(selector);
		var text=this.parse(file).compile;
		^
		selector.isSetter.if{ //write a file
			File.use(file, "w", {arg f;
				f.write(args.join($\n).asString)
			})
		}
		{ // transform the loaded file into object
			 var res=().make(
				try(text, 
					{ arg error;
						error.errorString.warn;
						Error("bug with file :  "++file).throw
					})
			);
			res.collect({|v,k|
				// TODO check if needed ? like ~var in def ?
				// replace glob vars with "var"
				// k.def.sourceCode.contains("~")
				//v.def.sourceCode.postln;
				v.class.switch(
					Function, {
						//						v.inEnvir(res)
						{ arg ... args;
							res.at(k).inEnvir(res).value(*args.drop(1))
						}
					},
					{v}
				)
			})
		}
	}
}

// for very lazy persons (aka me). It detects the actual app in the folder

Here{
	*entries{
		^PathName(thisProcess.nowExecutingPath)
		.entries.collect(_.fileName)
	}
	*all{ arg in=this.entries;
		in.do { |x|
			x.isFolder.if{this.all(x)}
			{""++(x.fileName)++(x.absolutePath.load.postcs)}
		}
	}
	*findRootDir{
		// we parse the dir of the call
		var loc=thisProcess.nowExecutingPath.dirname;
		//		var loc=Poly.root;
		var recursionLevel=0; // check upperDir
		var recurF={
			arg l=loc; var res;
			res=PathName(l).files.select({|x|
				x.fileName=="app.sc" })
			??? {
				recursionLevel=recursionLevel+1;
				if(recursionLevel < 2 )
				{recurF.value((l+/+".."))}
				{^"rien trouvé".warn}
			} 
		};
		var paths=recurF.value;
	}
	*doesNotUnderstand{ arg selector ... args;
		var paths=this.findRootDir;
		var res=paths.asArray.collect{ |path|
			var lines=
			File.open(path.absolutePath,"r")
			.readAllString.split(Char.nl);
			path ->
			{var line;
				line=lines.detectIndex{arg x; x.contains(": APP")};
				line !?
				{ lines[line].split($ )[0] } ?? {nil}
			}.value
		}.reject{|x| x.value.isNil};
		"classe trouvée : ".post;
		^res[0].value(selector, *args)
	}
}

Here2{
	*doesNotUnderstand{ arg selector ... args; 
		var here=thisProcess.nowExecutingPath;
		PathName(here).filesDo{ arg x;
			if(x.folderName==here.dirname){
				^x.absolutePath.load
			}
		}
	}
}




