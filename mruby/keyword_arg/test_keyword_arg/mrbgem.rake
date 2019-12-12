MRuby::Gem::Specification.new('test_keyword_arg') do |spec|
  spec.license = 'MIT'
  spec.author  = 'MRuby Developer'
  spec.summary = 'test_keyword_arg'
  spec.bins    = ['test_keyword_arg']

  spec.add_dependency 'mruby-print', :core => 'mruby-print'
  spec.add_dependency 'mruby-mtest', :mgem => 'mruby-mtest'
end
