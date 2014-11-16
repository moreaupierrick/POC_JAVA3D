


#? SHARED

#version 150
precision highp float;


#? VP

in vec2 vx_TexCoord;
out vec2 tx_TexCoord;

void main(void)

{
	gl_Position = vec4(vx_TexCoord*2.0-1.0,0.5,1.0);
	tx_TexCoord = vx_TexCoord;
}


#? FS

in vec2 tx_TexCoord;

out vec4 color;

uniform float alpha;

uniform sampler2D ColorTex;

uniform float type;

void main(void){

	if (type<0.5){
	
	// SUB INFO TYPE 
	
		ivec2 texSize = textureSize(ColorTex,0);
	
		if (gl_FragCoord.x>texSize.x || gl_FragCoord.y>texSize.y ){
			gl_FragDepth = 10;
			color = vec4(0,0,0,0);
		}
		else{
			
			float A = min(alpha,1.0);
			
			vec2 coord = gl_FragCoord.xy/texSize;
			
			if (A<0.999){
				
				A = A*A;
				 
				float wideX = (1.0-A)*0.006;
				float wideY = (1.0-A)*0.046875;
				
				color = vec4(0,0,0,0);
				for(float x= -wideX;x<=wideX;x+=wideX*0.5)
					for(float y= -wideY;y<=wideY;y+=wideY*0.5)
				 		color += texture(ColorTex, coord+vec2(x,y))*0.2*0.2;
				
			}else
				color = texture(ColorTex, coord);
			
			color *= A; 
			gl_FragDepth = 0;
		}
	}else{
	
		// SUB TITLE TYPE 
	
			
		float A = min(alpha,1.0);
			
		vec2 coord = tx_TexCoord;
		//coord.y*= 1.08;
		
		
		if (A<0.01)
			color= vec4(0,0,0,0);
		else
			color = texture(ColorTex, coord);
			 
		gl_FragDepth = 0;

		
	}



}
 
