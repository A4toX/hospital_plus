package com.hospital.flow.utils;

import cn.hutool.core.util.XmlUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.File;

/**
 * @author lgx
 */
public class BpmnUtils {

    public static void main(String[] args) {
        convertData();
    }

    public static void convertData() {
        Document document = XmlUtil.readXML(new File("C:/Users/69010/Desktop/diagram.BPMN"));
        NodeList nodeList = document.getElementsByTagName("bpmn2:startEvent");
        int nodeSize = nodeList.getLength();
        for (int i = 0; i < nodeSize; i++) {
            Node node = nodeList.item(i);
            Object id = node.getUserData("id");
            System.out.println();
        }
        System.out.println();

    }
}
