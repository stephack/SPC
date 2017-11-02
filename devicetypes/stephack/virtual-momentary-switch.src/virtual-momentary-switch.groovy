/**
 *  Virtual Speaker and Virtual Playlist Device Handler
 *
 *  Copyright 2017 Stephan Hackett
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 *
 *	
 */

metadata {
	definition (name: "Virtual Momentary Switch", namespace: "stephack", author: "Stephan Hackett") {
	capability "Switch"
	capability "Switch Level"
	capability "Sensor"
	capability "Actuator"
    capability "Momentary"
	}

	tiles(scale: 2) {
		multiAttributeTile(name: "switch", type: "lighting", width: 6, height: 4, canChangeBackground: true) {
			tileAttribute("device.switch", key: "PRIMARY_CONTROL") {
    			attributeState "off", label: '${name}', action: "switch.on", icon: "https://cdn.rawgit.com/stephack/sonosVC/master/resources/images/sp.png", backgroundColor: "#ffffff", nextState: "on"
		      	attributeState "on", label: '${name}', action: "switch.off", icon: "https://cdn.rawgit.com/stephack/sonosVC/master/resources/images/sp.png", backgroundColor: "#79b821", nextState: "off"
        	}
            tileAttribute ("level", key: "SLIDER_CONTROL") {
			attributeState "level", action:"setLevel"
		}
		}
    	standardTile("refresh", "device.switch", decoration: "flat", width: 2, height: 2) {
			state "default", label:"", action:"refresh.refresh", icon:"st.secondary.refresh"
		}
		main "switch"
		details(["switch","refresh"])

	}

}

def push() {
   	log.info "VMS "+"${getDataValue("vms")}"+" pushed"	
	sendEvent(name: "switch", value: "on", isStateChange: true, displayed: false)
	sendEvent(name: "switch", value: "off", isStateChange: true, displayed: false)
	sendEvent(name: "momentary", value: "pushed", isStateChange: true)
    parent.on(getDataValue("vms"))
}

def on() {		 
	push()
}

def off() {
	push()
}

def setLevel(val){
    log.info "VMS setLevel $val"
    parent.setLevel(val)
    sendEvent(name:"level",value:val)
}
