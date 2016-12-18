package junit;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.IOException;

public class RuleDemo {
    //����TemporaryFolder Rule
    //�����ڹ��췽���ϼ���·��������ָ����ʱĿ¼������ʹ��ϵͳ��ʱĿ¼
    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @Test
    public void testTempFolderRule() throws IOException {
        //��ϵͳ����ʱĿ¼�´����ļ�����Ŀ¼�������Է���ִ������Զ�ɾ��
        tempFolder.newFile("test.txt");
        tempFolder.newFolder("test");
    }


}
