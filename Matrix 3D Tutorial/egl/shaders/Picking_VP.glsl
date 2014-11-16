#version 150
precision highp float;

/***********************************************************************
MARC System - Affective interaction
Authors: Matthieu Courgeon

Copyright (C) 2010 LIMSI-CNRS (France)
This file is provided without support, instruction, or implied
warranty of any kind.  Authors make no guarantee of its
fitness for a particular purpose and are not liables under any
circumstances for any damages or loss whatsoever arising from the use
or inability to use this file or items derived from it.
*************************************************************************/


uniform mat4 ModelViewProj;

in vec4 vx_Position;

in vec2 vx_TexCoord;

out vec2 px_TexCoord;

void main() {

   gl_Position = ModelViewProj * vx_Position;
   px_TexCoord = vx_TexCoord;
}
