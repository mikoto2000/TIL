require_relative 'mrblib/module_test/version'

spec = MRuby::Gem::Specification.new('module_test') do |spec|
  spec.bins    = ['module_test']
  spec.add_dependency 'mruby-print', :core => 'mruby-print'
  spec.add_dependency 'mruby-mtest', :mgem => 'mruby-mtest'
end

spec.license = 'MIT'
spec.author  = 'MRuby Developer'
spec.summary = 'module_test'
spec.version = ModuleTest::VERSION
