require "./yaml_config.rb"

c = YamlConfig.new("yc.yaml")
c.set('test', 'test')
c.set('most/momost', 'momosts')
c.set('mast/mamast/mamasts', ['array', 'of', 'string!'])
c.set(['tast', 'tatast', 'tatasts'], 'array key')
c.save

p c.get('test')
p c.get('most/momost')
p c.get('mast/mamast/mamasts')
p c.get(['tast', 'tatast', 'tatasts'])

begin
    c.set({aaa: "bbb"}, "test")
rescue => e
    p "raise exception #{e}."
end

begin
    c.get({aaa: "bbb"})
rescue => e
    p "raise exception #{e}."
end
