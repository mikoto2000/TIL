# tree struct

## create project

`bundle init` and add `roo` in `Gemfile`.

```sh
bundle init
echo 'gem "roo"' >> .\Gemfile
```

## setup

`bundle install`.

```sh
bundle install --path=./vendor/bundle
```

# run

`bundle exec`.

```sh
> bundle exec ruby .\app.rb
{:config_name=>"/Tasks", :type=>"container", :name=>"Tasks"}
{:config_name=>"/Tasks/Task", :type=>"container", :name=>"LedOn"}
{:config_name=>"/Tasks/Task/Priority", :type=>"parameter", :value=>2}
{:config_name=>"/Tasks/Task/Preemption", :type=>"parameter", :value=>"FULL"}
{:config_name=>"/Tasks/Task/StackSize", :type=>"parameter", :value=>1024}
{:config_name=>"/Tasks/Task", :type=>"container", :name=>"LedOff"}
{:config_name=>"/Tasks/Task/Priority", :type=>"parameter", :value=>2}
{:config_name=>"/Tasks/Task/Preemption", :type=>"parameter", :value=>"FULL"}
{:config_name=>"/Tasks/Task/StackSize", :type=>"parameter", :value=>1024}
{:config_name=>"/Tasks/Task", :type=>"container", :name=>"InitTask"}
{:config_name=>"/Tasks/Task/Priority", :type=>"parameter", :value=>1}
{:config_name=>"/Tasks/Task/Preemption", :type=>"parameter", :value=>"NON"}
{:config_name=>"/Tasks/Task/StackSize", :type=>"parameter", :value=>1024}
{:config_name=>"/Tasks/Task/AutoStart", :type=>"parameter", :value=>true}
{:config_name=>"/Alarms", :type=>"container", :name=>"Alarms"}
{:config_name=>"/Alarms/Alarm", :type=>"container", :name=>"LedOffAlam"}
{:config_name=>"/Alarms/Alarm/ActivateTask", :type=>"container", :name=>"LedOff"}
{:config_name=>"/Alarms/Alarm/AutoStart", :type=>"container", :name=>"AutoStart"}
{:config_name=>"/Alarms/Alarm/AutoStart/Type", :type=>"parameter", :value=>"Cyclic"}
{:config_name=>"/Alarms/Alarm/AutoStart/Offset", :type=>"parameter", :value=>0}
{:config_name=>"/Alarms/Alarm/AutoStart/Period", :type=>"parameter", :value=>1}
{:config_name=>"/Alarms/Alarm", :type=>"container", :name=>"LedOnAlarm"}
{:config_name=>"/Alarms/Alarm/ActivateTask", :type=>"container", :name=>"LedOn"}
{:config_name=>"/Alarms/Alarm/AutoStart", :type=>"container", :name=>"AutoStart"}
{:config_name=>"/Alarms/Alarm/AutoStart/Type", :type=>"parameter", :value=>"Cyclic"}
{:config_name=>"/Alarms/Alarm/AutoStart/Offset", :type=>"parameter", :value=>0.5}
```

