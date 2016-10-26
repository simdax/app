// dataApp is a simple class for settings default params

DataAPP : ArchiveAPP {

	*initClass{
		Class.initClassTree(Library);
		StartUp.add{this.subclasses.do(_.read)};
		ShutDown.add{this.subclasses.do(_.write)};
	}

}


// saveApp is a way to save presets

SaveAPP : ArchiveAPP{
	classvar <saveDir;

	*initClass{
		this.subclasses.do(_.createSaveDir);
	} 
	*createSaveDir{
		//if already exists, do nothing
		saveDir=(this.root+/+"saves").mkdir;
	}	
	*write{ arg name;
		name ?? { name=Date.getDate.format("%D"++"@"++"%r")};
		super.write(name++".sctxar");
	}
	*read{ arg name;
		name !?
		{
			super.read(name)
		} ?? {
			super.read((this.root+/+"data.scd"))
		}
	}
	*open{
		EZListView().items_(PathName(saveDir).files);
	}
	
}