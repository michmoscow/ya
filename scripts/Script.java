package com.astro.onetable;

import java.io.IOException;

import com.astro.abell.AbellObject;
import com.astro.abell.HCGObject;
import com.astro.abell.ShObject;
import com.astro.chartlayers.PGCL;
import com.astro.chartlayers.Total;
import com.astro.hcngc.MainNGC;
import com.astro.hlbnldn.LBNProject;
import com.astro.hlbnldn.LDNProject;
import com.astro.hsac.SAGProject;
import com.astro.mch.MCH;
import com.astro.misc.Misc;
import com.astro.otherdbs.Cities;
import com.astro.otherdbs.EP;
import com.astro.simbaddata.PKProjectV;
import com.astro.simbaddata.UGCProject;
import com.astro.stars.Haas;
import com.astro.stars.HrCross;
import com.astro.stars.OrbElem;

/**
 * building all dbs in right order
 * @author leonid
 *
 */
public class Script {
	public static final String PATH="/home/leonid/DSOPlanner/LargeDb/CrossRef/Simbad/total/";

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		build1();
	}
	/**
	 * building databases somehow connected to cross names
	 * @throws Exception
	 */
	public static void build1() throws Exception{
		//ngcic
		MainNGC.build();
		copy(MainNGC.PATH+MainNGC.TEMP_HCNGC,PATH+MainNGC.TEMP_HCNGC);
		
		//messier, caldwell
		MCH.createMessier();
		MCH.createCaldwell();
		copy(MCH.PATHDB+MCH.M_DB,PATH+MCH.M_DB);
		copy(MCH.PATHDB+MCH.C_DB,PATH+MCH.C_DB);
		
		/*
		//abell
		AbellObject.main(null);
		copy(AbellObject.PATH+AbellObject.ABELL_DB,PATH+AbellObject.ABELL_DB);
		
		//HCG
		HCGObject.main(null);
		copy(HCGObject.PATH+HCGObject.HCG_DB,PATH+HCGObject.HCG_DB);
		
		//SH2
		ShObject.main(null);
		copy(ShObject.PATH+ShObject.SH2_DB,PATH+ShObject.SH2_DB);
		
		//UGC
		com.astro.hugc.UGCProject.build();
		copy(com.astro.hugc.UGCProject.PATH+com.astro.hugc.UGCProject.HUGC_DB,PATH+com.astro.hugc.UGCProject.HUGC_DB);
		
		//barnard. just copying the db from its folder. The db was made as follows:
		//excel,macros,text,import into dsoplanner, making db, taking it out
		copy("/home/leonid/DSOPlanner/LargeDb/Barnard/barnard.db",PATH+"barnard.db");
		
		//lbn
		LBNProject.fillDb();
		copy(LBNProject.PATH+LBNProject.HLBN_DB,PATH+LBNProject.HLBN_DB);
		
		//ldn
		LDNProject.fillDb();
		copy(LDNProject.PATH+LDNProject.HLDN_DB,PATH+LDNProject.HLDN_DB);
		
		//pk
		PKProjectV.build();
		copy(PKProjectV.PATH+PKProjectV.HPK_DB,PATH+PKProjectV.HPK_DB);
		
		//sac
		SAGProject.fill();
		copy(SAGProject.PATH+SAGProject.HSAC_DB,PATH+SAGProject.HSAC_DB);
		
		//pgcnames. no need to copy as copying is inside build method
		PGCNames.build();
		
		//pgc.db for dso selection. the file is created right in the necessary dir
		PGC.build();
		
		//building stars dbs. Some of dbs are copied to exp patch by external script
		Stars.build();//making total and comps.db. The former used in cross names. The latter copied by external script
		Haas.build();//building haas.db,copying wds data into it and putting there hr names
		
		//building final data dbs (with ref field) and cross.db including stars for dsoplanner.
		// uncomment //replaceUgcWithPgc(); if hugc.db has not been created yet
		com.astro.onetable.Main.build();
				
		*/
		
	}
	/**
	 * building other sqlite3 databases
	 * @throws Exception
	 */
	public static void build2() throws Exception{
		//hrcross.db for looking for hr stars by bayer and flamsteed names. 
		//Text file generated by com.astro.dsoplanner.misc.HrSearchDb
		HrCross.main(null);
		
		//making orbital elements db
		OrbElem.build();
		
		//location database
		Cities.build();
		
		//eyepieces database
		EP.create();
		
		//sgnotes
		com.astro.notes.Main.build();
		
		//misc. db needed for missing nsog
		Misc.create();
		
		//NSOG
		//files by constellations are copied to phone and com.astro.dsoplanner.misc.NSOG is run
		//2 text files are generated: the files for db creation and error files with missing objects
		//At the beginning a special misc.db was created from this file so that all objects are covered

	}
	/**
	 * building layers
	 * @throws Exception 
	 */
	public static void build3() throws Exception{
		//NGCIC - SAC layer
		Total.build(Total.PLUSPRO);
		
		//PGC layer
		PGCL.build();
	}
	
	public static void copy(String pathfrom,String pathto) throws IOException{
		String[] command=new String[]{"cp",pathfrom,pathto };		
		Runtime runTime = Runtime.getRuntime();
		runTime.exec(command);
	}

}
