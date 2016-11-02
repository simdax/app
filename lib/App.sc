APP{
	*root{
		^this.filenameSymbol.asString.dirname;
	}
	*doesNotUnderstand{ arg selector ... args;
		// TODO search in folder
		var file =
		PathName(this.root)
		.deepFiles() // it
		// TODO to avoid confusion, tell if multiple name ?
		.detect{arg x; x.fileName==(selector++".scd") }
		.absolutePath;
		^
		try({file.load.valueIfNeeded(*args)}, 
			{ arg error;
				error.errorString.warn;
				Error("bug with file : "++file).throw
			})
	}
	*entries{
		^PathName(this.root).entries.collect(_.fileName)
	}
	*all{
		^this.entries.do(_.load)
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
	// gotAppFile{
	// 	^
	// }
}

// for very lazy persons (aka me). It detects the actual app in the folder

Here{
	*doesNotUnderstand{ arg selector ... args;
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
		^res[0].value.asSymbol.inform.asClass.perform(selector, *args)

		
		
	}
}



IO {

	%%{arg a; ^a}
}



