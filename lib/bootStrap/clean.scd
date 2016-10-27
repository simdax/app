{
arg path,base, parent, proto;

[\files,\dirs].do { |symbol| 
	var r=base[symbol] =%% proto[symbol] =% parent;
	r.kvdo { |k,v|
		r.delete(k.asString,v.value(r))
	};
};
("bootStrap cleared at : "++path).inform
}

