package main;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.egl.libs.EGL_DisplayCanvas;
import org.egl.scene.EGL_Item;
import org.egl.scene.EGL_Light;
import org.egl.shapes.EGL_X3DShape;
import org.lwjgl.LWJGLException;


///---------------------------------------------------------------------------------
////  IMPORT A METTRE EN COMMENTAIRE LORSQUE VOUS ARRIVEZ A L'EXERCICE 2
///---------------------------------------------------------------------------------

//import main.correction.Matrix3D;

///---------------------------------------------------------------------------------
///---------------------------------------------------------------------------------


public class Matrix3DTutorial extends EGL_DisplayCanvas  
{

	private static final long serialVersionUID = 1L;

	public static final String TITLE = "Matrix Tutoral";

	static int SCREEN_W = 1000;

	static int SCREEN_H = 800;

	static int MAX_FRAMERATE = 30;


	// METHODE PRINCIPALE APPELEE AU DEMARRAGE 

	public static void main(String[] args) {

		// CREATION D'UNE FENETRE
		JFrame fenetre = new JFrame(TITLE);
		fenetre.getContentPane().setLayout(new BorderLayout());

		// CREATION DU COMPOSANT 3D OPENGL (AVEC GESTION DE L'ECHEC EVENTUEL)

		try {

			fenetre.getContentPane().add(new Matrix3DTutorial(),BorderLayout.CENTER);

		} catch (LWJGLException e) {
			// ECHEC
			JOptionPane.showMessageDialog(null, "OpenGL Context error...");
			e.printStackTrace(); //System.exit(0);
		}


		// AFFICHAGE DE LA FENETRE
		fenetre.setSize(Matrix3DTutorial.SCREEN_W,Matrix3DTutorial.SCREEN_H);
		fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fenetre.setLocationRelativeTo(null);
		fenetre.setVisible(true);
	}

	// CONSTRUCTEUR DU COMPOSANT 3D 

	public Matrix3DTutorial() throws LWJGLException {

		super(Matrix3DTutorial.SCREEN_W,Matrix3DTutorial.SCREEN_H, MAX_FRAMERATE,false,true);

		setBackground(Color.white);

	}


	//---------------------------------------------------------------------------------------------------
	//===============================================================================

	// METHODES IMPORTANTES --------------------------------------

	//===============================================================================
	//---------------------------------------------------------------------------------------------------

	EGL_Item ARTICULATION_1;
	EGL_Item ARTICULATION_2;
	EGL_Item ARTICULATION_3;
	EGL_Item ARTICULATION_4;
	EGL_Item ROUE_1;
	EGL_Item ROUE_2;
	EGL_Item ROUE_3;

	public void initScene() throws Exception{

		// DE PAS MODIFIER LE CODE DE CETTE FONCTION

		camera.setFrustum(-SCREEN_W/1000f,SCREEN_W/1000f,-SCREEN_H/1000f,SCREEN_H/1000f,1f,100);

		scene.addLight(new EGL_Light("").translateTo(0,200,200).setColor(1.0f,1.0f,1.0f));

		scene.addChild(ARTICULATION_1 = new EGL_X3DShape(this, null, "", "./object.x3d",true,false));

		scene.addChild(ARTICULATION_2 = new EGL_X3DShape(this, null, "", "./object.x3d",true,false));

		scene.addChild(ARTICULATION_3 = new EGL_X3DShape(this, null, "", "./object.x3d",true,false));

		scene.addChild(ARTICULATION_4 = new EGL_X3DShape(this, null, "", "./piston.x3d",true,false));

		scene.addChild(ROUE_1 = new EGL_X3DShape(this, null, "", "./roue.x3d",false,false));

		scene.addChild(ROUE_2 = new EGL_X3DShape(this, null, "", "./roue.x3d",false,false));

		scene.addChild(ROUE_3 = new EGL_X3DShape(this, null, "", "./roue.x3d",false,false));

	}


	// METHODE APPELEE ENTRE DEUX IMAGES POUR METTRE A JOUR LA SCENE 3D
	//===============================================================================
	//---------------------------------------------------------------------------------------------------



	float prog = 0;
	float angle = 0;

	float[] activeMatrix = new float[16];

	public void updateScene(){


		// NE PAS MODIFIER ---------------------------
		prog += 0.08f;
		angle = (float)(Math.cos(prog)*100.0);


		// PARTIE 1 - QUESTION 1 - LA POSITION DE LA CAMERA
		// ----------------------------------------------------------------------

		// FAIT - Initialiser la matrice active en matrice identit�
		
		Matrix3D.setIdentity(activeMatrix);

	
		// TODO - placer la camera � une hauteur de 10 et recul�e de 40 
		Matrix3D.translate(activeMatrix, 0, -10, -40);
	
		// TODO - Faire tourner la matrice active d'un angle de prog*10   
		Matrix3D.rotate(activeMatrix, 0,1, 0, prog*10);
	

		// PARTIE 1 - QUESTION 2 - ARTICULATION 1
		// ----------------------------------------------------------------------

		// TODO - Sauvegarder la matrice active   
		Matrix3D.pushMatrix(activeMatrix);
		
		// TODO - Effectuer une rotation de la matrice active d'un angle de 'angle' sur l'axe Z
		Matrix3D.rotate(activeMatrix, 0, 0, 1, angle);
	
		ARTICULATION_1.setMatrix(activeMatrix);


		// PARTIE 1 - QUESTION 3 - MOUVEMENT DU PISTON
		// ----------------------------------------------------------------------

		// TODO - Effectuer une translation de 10 sur l'axe Y
		Matrix3D.translate(activeMatrix, 0, 10, 0);
	
		// TODO - effectuer une roation d'un angle de '-angle' sur l'axeZ
		Matrix3D.rotate(activeMatrix, 0, 0, 1 , -angle);
	
		// TODO - Effectuer un effet d'echelle de ratio 1.2 sur tous les axes  
		Matrix3D.scale(activeMatrix, 1.2f);
	
		ARTICULATION_2.setMatrix(activeMatrix);

		// TODO - En vous inspirant du code de l'articulation 2, appliquer les meme transformations a l'articulation 3, 
		//        mais avec une echelle de '1.0/1.2' au lieu de '1.2'
	
		Matrix3D.translate(activeMatrix, 0, 10, 0);
		
		// TODO - effectuer une roation d'un angle de '-angle' sur l'axeZ
		Matrix3D.rotate(activeMatrix, 0, 0, 1 , -angle);
	
		// TODO - Effectuer un effet d'echelle de ratio 1.2 sur tous les axes  
		Matrix3D.scale(activeMatrix, 1.0f/1.2f);
	
		ARTICULATION_3.setMatrix(activeMatrix);


		// TODO - En vous inspirant du code de l'articulation 2, appliquer les meme transformations au Piston, 
		//        mais avec un angle qui permette au piston de rester vertical

		Matrix3D.translate(activeMatrix, 0, 10, 0);
		
		// TODO - effectuer une roation d'un angle de '-angle' sur l'axeZ
		Matrix3D.rotate(activeMatrix, 0, 0, 1 , angle);
	
		// TODO - Effectuer un effet d'echelle de ratio 1.2 sur tous les axes  
		Matrix3D.scale(activeMatrix, 1.2f);
		
		ARTICULATION_4.setMatrix(activeMatrix);


		// PARTIE 1 - QUESTION 4 - ROUE CRANTEE
		// ----------------------------------------------------------------------
		
		// TODO - Recuperer la matrice sauvegardee precedement.
		Matrix3D.popMatrix(activeMatrix);
	
		// ROUES CRANTEES

		// TODO - Effectuer une translation de 2 sur l'axe Z (pour d�caller les roues crant�es)
		Matrix3D.translate(activeMatrix, 0, 0, 2);
		
		// TODO - Sauvegarder la matrice active pour la r�utiliser pour les autres roues.
		Matrix3D.pushMatrix(activeMatrix);
		
		// TODO - Faire un effet d'echelle de ratio 5 sur tous les axes
		Matrix3D.scale(activeMatrix, 5);
		// TODO - Faire une rotation de la matrice active d'un angle de 'prog*20' sur l'axe Z
		Matrix3D.rotate(activeMatrix, 0, 0, 1, prog*20);
	
		ROUE_1.setMatrix(activeMatrix);
		
		
		// TODO - Recuperer la matrice sauvegard�e
		Matrix3D.popMatrix(activeMatrix);
		
		
		//// SECONDE ROUE --------------------------------------

		// TODO - Mettre en place a seconde roue crant�e. Sa position est (7.5,-2.5,0), son echelle est de '2.7', et sa rotation de '-prog*40+5'
		//        Ne pas oublier la sauvegarde et la r�cup�ration de matrice active pour la troisi�me roue.
		Matrix3D.pushMatrix(activeMatrix);
		Matrix3D.translate(activeMatrix, 7.5f, -2.5f, 0);
		Matrix3D.scale(activeMatrix, 2.7f);
		Matrix3D.rotate(activeMatrix, 0, 0, 1, -prog*40+5);
		
		ROUE_2.setMatrix(activeMatrix);
		

		// DERNIERE ROUE --------------------------------------
		
		// TODO - Mettre en place a derni�re roue crant�e. Sa position est (10,-10,0), son echelle est de '1.0', et sa rotation de 'prog*20'
		//        Ne pas oublier la sauvegarde et la r�cup�ration de matrice active pour la troisi�me roue.
		
		Matrix3D.popMatrix(activeMatrix);
		//Matrix3D.pushMatrix(activeMatrix);
		Matrix3D.translate(activeMatrix, 10f, -10f, 0);
		Matrix3D.scale(activeMatrix, 5.0f);
		Matrix3D.rotate(activeMatrix, 0, 0, 1, prog*20);
		
		ROUE_3.setMatrix(activeMatrix);

	}


}


