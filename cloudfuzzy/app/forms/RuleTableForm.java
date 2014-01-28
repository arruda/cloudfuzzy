 
	/*
	*
    * (c) 2013, 2014 INT - National Institute of Technology
	*
    * CloudFuzzy's team: 
	*	Dayse Mourao Arruda, Felipe Arruda Pontes, Bruno Barcellos Farias de Oliveira.
	*
    * The authors may be contacted by the email address: dayse.arruda@int.gov.br
	*
	* This file is part of CloudFuzzy - version Beta - 2014/January.
	*
    * We apply XFuzzy 3.0 as fuzzy engine to generate and evaluate alternative plans.
    * XFuzzy is a free design environment for fuzzy logic based systems distributed by IMSE-CNM.
	* If you need more information about the XFuzzy 3.0, please go to project's site:
	* http://www2.imse-cnm.csic.es/Xfuzzy/
    *                    
	* CloudFuzzy is free software: you can redistribute it and/or modify
	* it under the terms of the GNU General Public License as published by
	* the Free Software Foundation, either version 3 of the License, or
	* (at your option) any later version.
	*
	* CloudFuzzy is distributed in the hope that it will be useful,
	* but WITHOUT ANY WARRANTY; without even the implied warranty of
	* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
	* GNU General Public License for more details.
	*
	* You should have received a copy of the GNU General Public License
	* along with CloudFuzzy. If not, see <http://www.gnu.org/license/>.
    */
        
           
     
package forms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import models.FuzzySystem;

import play.data.validation.Constraints;

import xfuzzy.lang.ParamMemFunc;
import xfuzzy.lang.Specification;
import xfuzzy.lang.Relation;
import xfuzzy.lang.Rulebase;
import xfuzzy.lang.Rule;
import xfuzzy.lang.Conclusion;
import xfuzzy.lang.Variable;


public class RuleTableForm{
    
    /**
     * This ID is the position of the fuzzy mf in the array of
     * MFs in a type.
     */
    public Integer id;

    @Constraints.Required
    public Double degree;
    
    //a array of the id of the mfs used in each input
    public ArrayList<String> inputs;

    //a array of the id of the mfs used in each output
    public ArrayList<String> outputs;

    
    public Rule getFuzzyRule(Rulebase rb){
        Variable inputs[] = rb.getInputs();
        Variable outputs[] = rb.getOutputs();

        Relation premise = null;
        for(int j=inputs.length-1; j>=0; j--)  {
            //if the actual input is not blank in the mf selection

            if(!this.inputs.get(j).equals("")){
                //get the actual selected mf for the current input var
                ParamMemFunc imf = 
                    inputs[j].getType().getAllMembershipFunctions()[Integer.valueOf(this.inputs.get(j))];

                Relation rel = Relation.create(Relation.IS,null,null,inputs[j],imf,null);
                if(premise == null) premise = rel;
                else premise=Relation.create(Relation.AND,rel,premise,null,null,rb);
            }
        }

        Rule newrule = new Rule(premise,this.degree);
        for(int j=0; j<outputs.length; j++) {
            if(!this.outputs.get(j).equals("")){
                //get the actual selected mf for the current output var
                ParamMemFunc omf = 
                    outputs[j].getType().getAllMembershipFunctions()[Integer.valueOf(this.outputs.get(j))];
                newrule.add(new Conclusion(outputs[j],omf,rb));
            }
        }

       return newrule;
    }
    

    /**
    * This method will return all the relations of a given relation in a "reading" order.
    * left->right
    */

    public static ArrayList<xfuzzy.lang.Relation> getFuzzyRelationsAsList(xfuzzy.lang.Relation parentRel){

        // xfuzzy.lang.Relation parentRel=rule.getPremise();

        ArrayList<xfuzzy.lang.Relation> relations = new ArrayList<xfuzzy.lang.Relation>();

        if(parentRel!=null){
            xfuzzy.lang.Relation lRelation=parentRel.getLeftRelation();
            xfuzzy.lang.Relation rRelation=parentRel.getRightRelation();

            relations.addAll( getFuzzyRelationsAsList(lRelation) );
            relations.addAll( getFuzzyRelationsAsList(rRelation) );

            //if has no left or right relation, than is a node
            //then should add itself to the list and return.
            if(lRelation == null && rRelation == null){
                relations.add(parentRel);
            }

        }
        return relations;
    }

    public static ArrayList<Map<String,ArrayList<String>>> getInfoMapForTableRule(FuzzySystem sys, Integer id_rb){
        ArrayList<Map<String,ArrayList<String>>> infoMaps = new ArrayList<Map<String,ArrayList<String>>>();
         new HashMap<String, ArrayList<String>>();
        xfuzzy.lang.Rulebase fRB =null;
        try{
            fRB= models.RuleBase.getFuzzy(sys,id_rb);
        }
        catch(Exception e){
            return infoMaps;
        }

        for(int i =0; i < fRB.getRules().length; i++){
            xfuzzy.lang.Rule rule =  fRB.getRules()[i];

            Map<String, ArrayList<String>> ruleInfos = new HashMap<String, ArrayList<String>>();
            //id
            ArrayList<String> idInfos = new ArrayList<String>();
            idInfos.add( String.valueOf(i) );            
            ruleInfos.put("id", idInfos);

            //degree
            ArrayList<String> degreeInfos = new ArrayList<String>();
            degreeInfos.add( String.valueOf(rule.getDegree()) );
            ruleInfos.put("degree",degreeInfos);


            ArrayList<xfuzzy.lang.Relation> relations = RuleTableForm.getFuzzyRelationsAsList(rule.getPremise());

            //inputs
            ArrayList<String> inputVarsInfo = new ArrayList<String>();

            for(xfuzzy.lang.Variable ivar : fRB.getInputs()){
                String ivarInfo = "";
                //tries to find the relation of this input var
                for(xfuzzy.lang.Relation relation : relations){
                    if(relation.getVariable().getName().equals(ivar.getName())){
                        ivarInfo = relation.toXfl();
                        break;
                    }
                }

                inputVarsInfo.add(ivarInfo);
            }
            ruleInfos.put("inputs",inputVarsInfo);


            //outputs
            ArrayList<String> outputVarsInfo = new ArrayList<String>();

            for(xfuzzy.lang.Variable ovar : fRB.getOutputs()){
                String ovarInfo = "";
                //tries to find the conclusion of this out var
                for(xfuzzy.lang.Conclusion conclusion : rule.getConclusions()){
                    if(conclusion.getVariable().getName().equals(ovar.getName())){
                        ovarInfo = conclusion.toXfl();
                        break;
                    }
                }

                outputVarsInfo.add(ovarInfo);
            }
            ruleInfos.put("outputs",outputVarsInfo);


            infoMaps.add(ruleInfos);
        }


        return infoMaps;
    }
}
