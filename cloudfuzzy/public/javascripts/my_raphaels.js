

	Raphael.fn.arrow = function (x1, y1, x2, y2, size) {
		var angle = Math.atan2(x1-x2,y2-y1);
		angle = (angle / (2 * Math.PI)) * 360;
		var arrowPath = this.path("M" + x2 + " " + y2 + " L" + (x2  - size) + " " + (y2  - size) + " L" + (x2  - size)  + " " + (y2  + size) + " L" + x2 + " " + y2 ).attr("fill","black").rotate((90+angle),x2,y2);
		var linePath = this.path("M" + x1 + " " + y1 + " L" + x2 + " " + y2);
		return [linePath,arrowPath];
	};


	Raphael.fn.arrow_elem = function(base, origin, destiny, size) {
		console.log("origin");
		console.log(origin);
		console.log("destiny");
		console.log(destiny);
		
		var x0 = origin.offset().left - base.offset().left;// + 4;
		var y0 = origin.offset().top -  base.offset().top;// + 4;
			
		var x1 = destiny.offset().left - base.offset().left;// + 4;
		var y1 = destiny.offset().top - base.offset().top;// + 4;
		console.log("x0:" + x0 + " y0:" + y0 +" x1:" + x1 +" y1:" + y1);
		return this.arrow(x0,y0,x1,y1,size); 
	};