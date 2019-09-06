/*
 * Created on Sep 29, 2004
 *
 */
package ru.filippov.utils;



import lombok.Data;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * Holds an object representation of the NEAT settings
 * @author MSimmerson
 *
 */
@Data
public class NEATConfig implements AIConfig {
	private Map settings;



	//Make copy of settings
	public NEATConfig(NEATConfig originalConfig){
		this.settings = new HashMap(originalConfig.settings);
	}

	
	public NEATConfig(Map config) {
		this.settings = new HashMap(config);

	}	
		
	// default
	public NEATConfig() {
		this.settings = new HashMap(50);
	}

	public NEATConfig(String filePath) {
		this.settings = new HashMap();
	}

	public String configElement(String elementKey) {
		return ((String)this.settings.get(elementKey));
	}

	public void updateConfig(String elementKey, String elementValue) {
		this.settings.put(elementKey, elementValue);
	}

	@Override
	public boolean saveConfig(File dest) throws IOException {
		if(dest != null) {
			BufferedWriter writer = new BufferedWriter(new FileWriter(dest));
			writer.write("");
			this.settings.entrySet().stream().forEach(o -> {
				Map.Entry<String, String> entry = (Map.Entry<String, String>) o;
				try {
					writer.append(entry.getKey() +"="+entry.getValue()+"\n");
				} catch (IOException e) {
					e.printStackTrace();
				}

			});
			writer.flush();
			writer.close();
			return true;
		} else {
			return false;
		}
	}

	public List<String> getActivationFunctionsByElementKey(String elementKey){
		StringTokenizer stringTokenizer = new StringTokenizer(this.configElement(elementKey), ";");
		List<String> activationFunctions = new ArrayList<>(stringTokenizer.countTokens());
		while (stringTokenizer.hasMoreTokens()){
			activationFunctions.add(stringTokenizer.nextToken());
		}
		return activationFunctions;

	}


	public static AIConfig getDefaultConfig(){
		Map<String, Object> config = new HashMap<>(50);
		config.put("PROBABILITY_MUTATION",0.25);
		config.put("PROBABILITY_CROSSOVER",0.5);
		config.put("PROBABILITY_ADDLINK",0.1);
		config.put("PROBABILITY_ADDNODE",0.03);
		config.put("PROBABILITY_NEWACTIVATIONFUNCTION",0.1);
		config.put("PROBABILITY_MUTATEBIAS",0.3);
		config.put("PROBABILITY_TOGGLELINK",0.1);
		config.put("PROBABILITY_WEIGHT_REPLACED",0.5);
		config.put("GENERATOR_SEED",1548235723799L);
		config.put("EXCESS_COEFFICIENT",1);
		config.put("DISJOINT_COEFFICIENT",1);
		config.put("WEIGHT_COEFFICIENT",0.4);
		config.put("COMPATABILITY_THRESHOLD",0.5);
		config.put("COMPATABILITY_CHANGE",0.1);
		config.put("SPECIE_COUNT",3);
		config.put("SURVIVAL_THRESHOLD",0.2);
		config.put("SPECIE_AGE_THRESHOLD",80);
		config.put("SPECIE_YOUTH_THRESHOLD",10);
		config.put("SPECIE_OLD_PENALTY",1.2);
		config.put("SPECIE_YOUTH_BOOST",0.7);
		config.put("SPECIE_FITNESS_MAX",15);
		config.put("MAX_PERTURB",0.5);
		config.put("MAX_BIAS_PERTURB",0.1);
		config.put("FEATURE_SELECTION",false);
		config.put("RECURRENCY_ALLOWED",false);
		config.put("INPUT_ACTIVATIONFUNCTIONS","org.neat4j.neat.nn.core.functions.LinearFunction;;");
		config.put("OUTPUT_ACTIVATIONFUNCTIONS",";org.neat4j.neat.nn.core.functions.SigmoidFunction;");
		config.put("HIDDEN_ACTIVATIONFUNCTIONS",";;org.neat4j.neat.nn.core.functions.TanhFunction");
		config.put("ELE_EVENTS",false);
		config.put("ELE_SURVIVAL_COUNT",0.1);
		config.put("ELE_EVENT_TIME",1000);
		config.put("KEEP_BEST_EVER",true);
		config.put("EXTRA_FEATURE_COUNT",0);
		config.put("POP_SIZE",150);
		config.put("NUMBER_EPOCHS",100);
		config.put("TERMINATION_VALUE_TOGGLE",false);
		config.put("TERMINATION_VALUE",0.00001);
		config.put("NATURAL_ORDER_STRATEGY",true);
		return new NEATConfig(config);
	}
}
