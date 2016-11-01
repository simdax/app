+ String{

	capitalize{
		^this[0].toUpper++this.drop(1)
	}
	
	loadPaths { arg warn = true, action;
		var paths = this.pathMatch;
		if(warn and:{paths.isEmpty}) { ("no files found for this path:" + this.quote).warn };
		^paths.collect({ arg path;
			var result = thisProcess.interpreter.executeFile(path);
			action.value(path, result);
			// here adding unbubble
			result
		}).unbubble;
	}
}


