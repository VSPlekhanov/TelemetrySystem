# Azul telemetry system java agent

This java agent is for sending information about JVM runtime. For now, agent collects information about:
* system properties
* environment variables
* virtual machine
* loaded classes


## Installation

* Download the [latest release](ADD LINK HERE)
* Run your Java application with the JVM argument `-javaagent:agent-<version>.jar[=<Optional configuration parameters>]`

## Configuration

Configuration properties can be specified either as environment variables or with console arguments. Properties from console arguments take precedence.



* `telemetry.agent.id` - Your personal id in telemetry system
* `telemetry.agent.authtoken` - Your personal token for authorization
* `telemetry.agent.enabled` - true if you want to run javaagent and false otherwise
* `telemetry.agent.initial_delay` - Initial delay in milliseconds before regular updates start
* `telemetry.agent.telemetry_interval` - Interval in milliseconds between regular updates

If you specify properties with console arguments, you should enumerate them with the following format: `key1=value1&key2=value2` etc.
Example:
`-javaagent:agent-1.0.jar=telemetry.agent.id=1&telemetry.agent.authtoken=smth`