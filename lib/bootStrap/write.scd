{
arg path,base, parent, proto;

[\files,\dirs].do { |symbol| 
	var r=base[symbol] =%% proto[symbol] =% parent;
	r.kvdo { |k,v|
		r.write(k.asString,v.value(r))
	};
};
("bootStrap executed at : "++path).inform
}