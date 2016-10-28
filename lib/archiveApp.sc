// ArchiveApp is an interface between files and Library class
// like multiple Archive via Library archiving

ArchiveAPP : APP {
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
	*clear{
		Library.put(this.name, ())
	}
}

