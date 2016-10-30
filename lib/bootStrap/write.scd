{
	arg path,base, parent, proto;

	var f=thisFunction;
	base.kvdo{ arg k,v;
		k.switch(
			\files, { 
				var r= v =%% proto[\files] =% (parent);
				r.kvdo { |k,v|
					r.write(k.asString,v.value(r))
				};
			},
			\dirs, {
				var r= (v =%% proto[\dirs] =% parent);
				r.kvdo { |k,v|
					r.path.postln; r.root.postln;
					r.write(k.asString.postln);
					if(v.value(r)!=())
					{				
						f.value(path+/+k,v.value(r),parent.copy.path_(path+/+k),proto)
					};
				};		
			},
			{"unknow key?".warn}
		)
	};
	("bootStrap executed at : "++path).inform
}