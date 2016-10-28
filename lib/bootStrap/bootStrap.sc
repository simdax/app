BootStrap : APP{
	*find{arg name;
		var here;
		^name !? {
			here=Platform.userExtensionDir+/+name;
			if(PathName(here).isFolder.not, {
				here.mkdir;
			})
		} ??
		{here=thisProcess.nowExecutingPath};		
	}
	*new{ arg name;
		var here=this.find(name);
		BootStrap.live(here,\write)
	}
	*clear{ arg name;
		here=this.find(name);
		BootStrap.live(here,\clean)
	}
}

