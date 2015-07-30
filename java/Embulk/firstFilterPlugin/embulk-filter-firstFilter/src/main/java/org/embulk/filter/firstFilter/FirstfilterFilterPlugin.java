package org.embulk.filter.firstFilter;

import com.google.common.base.Optional;
import org.embulk.config.Config;
import org.embulk.config.ConfigDefault;
import org.embulk.config.ConfigDiff;
import org.embulk.config.ConfigSource;
import org.embulk.config.Task;
import org.embulk.config.TaskSource;
import org.embulk.spi.Column;
import org.embulk.spi.Exec;
import org.embulk.spi.FilterPlugin;
import org.embulk.spi.Page;
import org.embulk.spi.PageBuilder;
import org.embulk.spi.PageOutput;
import org.embulk.spi.PageReader;
import org.embulk.spi.Schema;
import org.embulk.spi.type.Types;

public class FirstfilterFilterPlugin
        implements FilterPlugin
{
    public interface PluginTask
            extends Task
    { }

    @Override
    public void transaction(ConfigSource config, Schema inputSchema,
            FilterPlugin.Control control)
    {
        System.out.println("transaction!");
        System.out.print("config: ");
        System.out.println(config);
        System.out.print("inputSchema: ");
        System.out.println(inputSchema);
        System.out.print("control: ");
        System.out.println(control);
        PluginTask task = config.loadConfig(PluginTask.class);

        java.util.List<Column> newSchemaColumns = inputSchema.getColumns();
        Schema.Builder builder = Schema.builder();

        // 連番カラムを追加
        builder.add("lineNumber", Types.LONG);
        for (Column column : newSchemaColumns) {
            builder.add(column.getName(), column.getType());
        }
        // 追加文字列カラムを追加
        builder.add("additional", Types.STRING);

        Schema outputSchema = builder.build();

        control.run(task.dump(), outputSchema);
    }

    @Override
    public PageOutput open(TaskSource taskSource, Schema inputSchema,
            Schema outputSchema, PageOutput output)
    {
        System.out.println("open!");
        System.out.print("taskSource: ");
        System.out.println(taskSource);
        System.out.print("inputSchema: ");
        System.out.println(inputSchema);
        System.out.print("outputSchema: ");
        System.out.println(outputSchema);
        System.out.print("output: ");
        System.out.println(output);
        PluginTask task = taskSource.loadTask(PluginTask.class);
        System.out.print("task: ");
        System.out.println(task);

        return new MyPageOutput(inputSchema, outputSchema, output);
    }
}

class MyPageOutput implements PageOutput {
    private Schema inputSchema;
    private PageBuilder pageBuilder;
    private long lineNumber = 1;

    public MyPageOutput(Schema inputSchema, Schema outputSchema, PageOutput originalPageOutput) {
        this.inputSchema = inputSchema;
        this.pageBuilder = new PageBuilder(Exec.getBufferAllocator(),
                        outputSchema,
                        originalPageOutput);
    }

    @Override
    public void add(Page page) {
        System.out.println("start MyPageOutput#add!");
        try (PageReader pageReader = new PageReader(inputSchema)) {
            pageReader.setPage(page);

            System.out.println("record count: " + PageReader.getRecordCount(page));

            int columnCount = inputSchema.getColumnCount();

            while (pageReader.nextRecord()) {
                // 行番号カラム
                pageBuilder.setLong(0, lineNumber);

                // 入力カラムたち
                pageBuilder.setLong(1, pageReader.getLong(0));
                pageBuilder.setLong(2, pageReader.getLong(1));
                pageBuilder.setTimestamp(3, pageReader.getTimestamp(2));
                pageBuilder.setTimestamp(4, pageReader.getTimestamp(3));
                pageBuilder.setString(5, pageReader.getString(4));

                // 追加文字列カラム
                pageBuilder.setString(6, "Additional String" + lineNumber++ + "!");

                // 編集したレコードを追加
                pageBuilder.addRecord();
            }
        }
    }

    @Override
    public void close() {
        System.out.println("start PageOutput#close.");
        pageBuilder.close();
    }

    @Override
    public void finish() {
        System.out.println("start PageOutput#finish.");
        pageBuilder.finish();
    }
}
