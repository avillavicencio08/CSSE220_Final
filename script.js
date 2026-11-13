			class Grapher {
				#canvas;
				#ctx;

				constructor() {
					this.#canvas = document.getElementById("graph");
					this.#ctx = this.#canvas.getContext("2d");
					this.#ctx.fillStyle = "#ffffff";
					this.#ctx.fillRect(0, 0, 500, 500);
					//this.#ctx.fillRect(1, 1, 499, 499);
					this.#ctx.fillStyle = "#000000";
				}

				drawLine(x1, y1, x2, y2) {
					this.#ctx.fillRect(x1, y1, 3, 3);
					if(x1 > x2) {
						let temp = x1;
						x1 = x2;
						x2 = temp;
						temp = y1;
						y1 = y2;
						y2 = temp;
					}
					let slope = (y2 - y1)/(x2 - x1);
					for(let i = 0; i < 2000; ++i) {//while(((((x1 - 5) < x2) != true) && (((x1 + 5) > x2) != true)) && ((((y1 - 5) < y2) != true) && (((y1 + 5) > y2) != true))) {
						this.#ctx.fillRect(x1, y1, 3, 3);
						x1 += 1;
						y1 += slope;
					}
					this.#ctx.fillRect(x2, y2, 3, 3);
				}
			}
			let myGrapher = new Grapher();
			myGrapher.drawLine(0, 0, 400, 400);
			myGrapher.drawLine(400, 0, 0, 400);
