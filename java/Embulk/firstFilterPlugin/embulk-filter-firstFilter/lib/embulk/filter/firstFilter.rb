Embulk::JavaPlugin.register_filter(
  "firstFilter", "org.embulk.filter.firstFilter.FirstfilterFilterPlugin",
  File.expand_path('../../../../classpath', __FILE__))
