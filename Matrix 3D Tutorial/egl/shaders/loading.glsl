


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

out vec4 color_out;

uniform float progression ;

uniform sampler2D ColorTex;
uniform sampler2D progressTex;
uniform vec2 ImageScale;


vec2 getTC(vec2 intc){

	vec2 texCoord = intc;
	
	texCoord -= 0.5;
	texCoord *= ImageScale;
	
	texCoord += 0.5;
	
	texCoord.x = texCoord.x>1.0?1.0:texCoord.x<0.0?0.0:texCoord.x;
	texCoord.y = texCoord.y>1.0?1.0:texCoord.y<0.0?0.0:texCoord.y;
	
	return texCoord;
	
}

void main(void){

	if (tx_TexCoord.y<0.03 && tx_TexCoord.x<progression){
		color_out = texture(progressTex, tx_TexCoord*vec2(1.0,1.0/0.03));
		return;
	}

	
	vec2 blurV = tx_TexCoord-0.5;
	blurV.x -= progression-0.5;
	

	vec3 v = vec3(0,0,0);
	
	float sum = 0;
	
	for(float i=-50; i<=0; i++){
		v += texture(ColorTex, getTC(tx_TexCoord + blurV*0.004*i) ).rgb / (1.5+abs(i)) ;
		sum += 1.0/(1.5+abs(i));
	}
	
	
	color_out = vec4(v/sum,1.0);
	
}
 
