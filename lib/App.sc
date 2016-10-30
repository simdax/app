APP{
	*root{
		^this.filenameSymbol.asString.dirname;
	}
	*doesNotUnderstand{ arg ...args;
		// TODO search in folder
		var file = (this.root+/+args[0]++".scd");
		^
		try({file.load.value(*args.drop(1))}, 
			{ arg error;
				error.errorString.postln;
				Error("bug with file : "++file).throw
			})
	}
	*entries{
		^PathName(this.root).entries.collect(_.absolutePath)
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
		var loc=thisProcess.nowExecutingPath.postln.dirname.postln;
		//		var loc=Poly.root;
		var paths=PathName(loc).files.select({|x| x.extension=="sc" });
		var res=paths.collect{ |path|
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
		// with multi app ???		
		if(res.size>1, {
			^Error("HERE is not clear enough. Here are your choices :"++ res)
			.throw
		},{
			^res[0].value.asSymbol.asClass.perform(selector, *args)
		});
		
		
	}
}



IO {

	%%{^2}
}

