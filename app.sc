APP{

	*root{
		^this.filenameSymbol.asString.dirname;
	}
	*doesNotUnderstand{ arg ...args;
		// TODO search in folder
		var file = (this.root+/+args[0]++".scd");
		^
		try{file.load}
		?? {
			Error("no File at "++file).throw
		}
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
}
