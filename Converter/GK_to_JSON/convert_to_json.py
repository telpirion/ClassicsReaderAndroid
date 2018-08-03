#!/usr/bin/env python
# -*- coding: utf-8 -*-

from codecs import open
import json
import convert
import os
import re
import sys
import xml.etree.ElementTree as ET

'''
Structure of JSON output:
{
    'title': String,
    'author': String,
    'toc': [
        {
            'section_type': String,
            'section_num': Number
        },
    ],
    'sections': [
        {
            'section_type': String,
            'section_num': Number,
            'metadata': {},
            'contents': [
                {
                    'section_type': String,
                    'text': String,
                },
            ]
        },
    ]
}

'''

def create_book_json(book, section_type, content_type):
    section = {}
    contents = []
    section['section_type'] = section_type
    section['section_num'] = book.get('n')

    count = 1;
    lines = ''
    for line in book.iter('p'):
        lines += line.text + '\n'
        count += 1

        if (count % 5) is 0:
            content = {
                'content_type': content_type,
                'text': lines
            }

            lines = ''
            contents.append(content)

    section['contents'] = contents
    return section

def convert_to_json(source_file, output_file, section_type, content_type, book_el):
    ''' Converts XML of Iliad to JSON.
    '''
    work = {}
    sections = []

    source = convert.openXMLSource(source_file)
    work['title'] = (source[0]
        .find('fileDesc')
        .find('titleStmt')
        .find('title')
        .text)
    work['author'] = (source[0]
        .find('fileDesc')
        .find('titleStmt')
        .find('author')
        .text)

    toc = []


    books = source[1][0].iter(book_el)

    for book in books:
        toc_entry = {
            'section_num': book.get('n'),
            'section_type': section_type
        }
        toc.append(toc_entry)
        book_json = create_book_json(book, section_type, content_type)
        sections.append(book_json)

    work['toc'] = toc
    work['sections'] = sections
    print(json.dumps(work))

    with open(output_file, 'w', encoding='utf-8') as output:
        json.dump(work, output, ensure_ascii=False)

if __name__ == '__main__':
    print('Starting process')
    (convert_to_json('../GK_to_XML/output/gk_hom_il_gk.xml',
        'output/hom_il_gk.json',
        'book',
        '5-lines',
        'div1'))
    print('Process finished.')