/*
 * ---------------------------------------------------------------------------------
 * (C) Graham Johnson (orangebucket)
 *
 * Permission to use, copy, modify, and/or distribute this software for any purpose
 * with or without fee is hereby granted, provided that the copyright notice below
 * and this permission notice appear in all copies.
 *
 * THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES WITH 
 * REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF MERCHANTABILITY AND 
 * FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY SPECIAL, DIRECT,
 * INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES WHATSOEVER RESULTING FROM LOSS
 * OF USE, DATA OR PROFITS, WHETHER IN AN ACTION OF CONTRACT, NEGLIGENCE OR OTHER 
 * TORTIOUS ACTION, ARISING OUT OF OR IN CONNECTION WITH THE USE OR PERFORMANCE OF
 * THIS SOFTWARE.
 * ---------------------------------------------------------------------------------
 *
 * Anidea for Virtual Presence
 * ===========================
 * Version:	 20.05.10.00
 *
 * A virtual presence and occupancy sensor that handles the two capabilities separately. 
 * Custom commands use standard names where available.
 */

metadata 
{
    definition ( name: 'Anidea for Virtual Presence', namespace: 'orangebucket', author: 'Graham Johnson' ) 
    {
    	// The DTH can work as both a Presence Sensor and an Occupancy Sensor.  The two
        // capabilities are not linked within the handler.
        capability 'Presence Sensor'
        capability 'Occupancy Sensor'
        
        capability 'Sensor'
        capability 'Health Check'

		// Custom commands for setting the presence, named for compatibility with
        // the stock Simulated Presence Sensor DTH as a de facto standard.
        command 'arrived'
        command 'departed'

		// Custom commands for setting occupancy.  No known standards to follow.
		command 'occupied'
        command 'unoccupied'
    }
}

def installed()
{
    logger( 'installed', 'info', '' )
    
    // Set up Health Check using the untracked enrollment method, copied from stock handlers.
    sendEvent( name: 'DeviceWatch-Enroll', value: [protocol: "cloud", scheme:"untracked"].encodeAsJson(), displayed: false ) 

	// Initialise the attributes to keep the SmartThings app happy. Set the defaults
    // to the values most likely to keep things secure.
    sendEvent( name: 'presence',  value: 'not present', displayed: false )
    sendEvent( name: 'occupancy', value: 'unoccupied',  displayed: false )
}

def updated() 
{
    logger( 'updated', 'info', '' )
}

def logger( method, level = 'debug', message = '' )
{
	log."${level}" "$device.displayName [$device.name] [${method}] ${message}"
}

// This should be redundant.
def parse( description )
{
    logger( 'parse', 'info', description )
}

def arrived() 
{
    logger( 'arrived', 'info', '' )
    
    sendEvent( name: 'presence', value: 'present' )
}


def departed() 
{
    logger( 'departed', 'info', '' )
    
    sendEvent( name: 'presence', value: 'not present' )
}

def occupied() 
{
    logger( 'occupied', 'info', '' )
    
    sendEvent( name: 'occupancy', value: 'occupied' )
}

def unoccupied() 
{
    logger( 'unoccupied', 'info', '' )
    
    sendEvent( name: 'occupancy', value: 'unoccupied' )
}