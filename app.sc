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
			Error("no File at : "++file).throw
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

DataAPP : APP {

	*initClass{
		Class.initClassTree(Library);
		StartUp.add{this.subclasses.do(_.read)};
		ShutDown.add{this.subclasses.do(_.write)};
	}

	*read { arg filename;
		var expandedFileName;
		expandedFileName = filename ?? (this.root ++ "/archive.sctxar");
		if (File.exists(expandedFileName)) {
			if (expandedFileName.endsWith(".scar")) {
				Library.put(this.name, this.readBinaryArchive(expandedFileName));
			}{
				Library.put(this.name,this.readArchive(expandedFileName));
			}
		}
		{
			Library.put(this.name, ());
		}
	}
	*write { arg filename;
		var expandedFileName;
		expandedFileName = filename ?? (this.root ++ "/archive.sctxar");
		Library.at(this.name).writeArchive(expandedFileName);
	}
	*save{ arg ... args;
		Library.put(this.name, *args)
	}
}

SaveAPP : DataAPP{
	classvar <saveDir, <nbSaves;

	*initClass{
		this.subclasses.do(_.createSaveDir);
	} 
	*createSaveDir{
		//if already exists, do nothing
		saveDir=(this.root+/+"saves").mkdir;
		nbSaves=PathName(saveDir).files.size
	}	
	*write{ 
		super.write(saveDir+/+nbSaves++".sctxar");
		nbSaves=nbSaves+1;
	}
	*open{
		EZListView().items_(PathName(saveDir).files);
	}
	
}